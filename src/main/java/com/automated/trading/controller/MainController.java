package com.automated.trading.controller;

import java.sql.Timestamp;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.automated.trading.alpaca.PlaceOrder;
import com.automated.trading.exception.serviceexceptions.UserServiceNewUserInfoTooLongException;
import com.automated.trading.exception.serviceexceptions.UserServiceNoUserEmailFoundException;
import com.automated.trading.exception.serviceexceptions.UserServicePasswordIncorrectException;
import com.automated.trading.exception.serviceexceptions.UserServiceUserAlreadyExistsException;
import com.automated.trading.model.Order;
import com.automated.trading.model.User;
import com.automated.trading.service.OrderService;
import com.automated.trading.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tradehook/")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PlaceOrder placeOrder;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // This corresponds to 'register.html' in /templates
    }

    @PostMapping("user/create")
    public ResponseEntity<?> createUser(@RequestBody User user,HttpSession session) {
        try {
            User createdUser = userService.createNewUser(user);
            User returnedUser = new User(createdUser.getEmail());
            session.setAttribute("email", createdUser.getEmail());
            return new ResponseEntity<>(returnedUser, HttpStatus.CREATED);  // Return user with 201 Created status
        } catch (UserServiceNewUserInfoTooLongException | UserServiceUserAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>((e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());  // for form binding
        return "login";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Ends the session
        return "redirect:/tradehook/login"; // Redirect to login page
    }


    @PostMapping("authenticate-user")
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
        try {
            User loginUser = userService.login(user);
            User returnedUser = new User(loginUser.getEmail());
            session.setAttribute("email", loginUser.getEmail());
            return new ResponseEntity<>(returnedUser, HttpStatus.OK);
        } catch (UserServiceNoUserEmailFoundException | UserServicePasswordIncorrectException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/home")
    public String showHomePage(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/tradehook/login"; // redirect to login if not authenticated
        }
        return "home"; // this corresponds to 'home.html' in /templates
    }



    @PutMapping("user/alpaca-keys/update")
    public ResponseEntity<String> updateUserAlpacaKeys(@RequestBody User user, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        user.setEmail(email);
        User updatedUser = userService.updateAlpacaApiKeys(user);
        if (updatedUser != null) {
            return new ResponseEntity<>("Alpaca Api Keys Have Been Updated Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update Alpaca keys", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user/tradehook-key")
    public ResponseEntity<?> getUserTradehookKey(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        String key = userService.selectUserByEmail(new User(email)).getTradehookApiKey();
        return new ResponseEntity<>(key, HttpStatus.OK);
    }

    @PutMapping("user/tradehook-key/update")
    public ResponseEntity<String> updateUserTradehookKey(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        User user = new User(email);
        userService.updateTradehookApiKey(user);
        User selectedUser = userService.selectUserByEmail(user);
        String newKey = selectedUser.getTradehookApiKey();
        return new ResponseEntity<>(newKey, HttpStatus.OK);
    }

    @GetMapping("order/list")
    public ResponseEntity<?> listOrders(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        User currentUser = userService.selectUserByEmail(new User(email));
        Integer userId = currentUser.getId();
        return new ResponseEntity<>(orderService.selectAllOrdersByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("order/create")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> requestBody) {
        // Extract tradehookApiKey and other order details from the request map
        String tradehookApiKey = (String) requestBody.get("tradehookApiKey");
        String ticker = (String) requestBody.get("ticker");
        String qty = (String) requestBody.get("qty");
        String side = (String) requestBody.get("side");

        // Find the user by tradehookApiKey
        User selectedUser = userService.selectUserByTradehookApiKey(tradehookApiKey);

        // Handle the case where no user was found
        if (selectedUser == null) {
            return new ResponseEntity<>("User not found for provided Tradehook API Key", HttpStatus.BAD_REQUEST);
        }

        // Get Alpaca API keys for the selected user
        String alpacaApiKey = selectedUser.getAlpacaApiKey();
        String alpacaSecretKey = selectedUser.getAlpacaSecretKey();

        // Place the order using Alpaca API
        net.jacobpeterson.alpaca.openapi.trader.model.Order createdOrder = placeOrder.placeSimpleOrder(ticker, qty, side, alpacaApiKey, alpacaSecretKey);

        // Handle the case where no order was created
        if (createdOrder == null) {
            return new ResponseEntity<>("No Order Created", HttpStatus.BAD_REQUEST);
        } else {
            // Map the created order details into your local Order model
            Integer userId = selectedUser.getId();
            String orderId = createdOrder.getId();
            String returnedTicker = createdOrder.getSymbol();
            String returnedQty = createdOrder.getQty();
            Timestamp createdAt = Timestamp.valueOf(createdOrder.getCreatedAt().toLocalDateTime());
            String returnedSide = createdOrder.getSide().toString();

            // Create the order to persist in your database
            Order orderToPersist = new Order(userId, orderId, returnedTicker, returnedQty, createdAt, returnedSide);

            // Persist the order
            Order persistedOrder = orderService.createNewOrder(orderToPersist);
            Order returnedOrder = new Order(persistedOrder.getOrderId(), persistedOrder.getTicker(), persistedOrder.getQty(), persistedOrder.getCreatedAt(), persistedOrder.getSide());

            // Return the persisted order with a 201 Created status
            return new ResponseEntity<>(returnedOrder, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("user/delete")
    public ResponseEntity<String> deleteUser(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        User currentUser = new User(email);
        User selectedUser = userService.selectUserByEmail(currentUser);
        orderService.deleteOrdersByUserId(selectedUser.getId());
        userService.deleteUserByEmail(currentUser);
        return new ResponseEntity<>("User Deleted",HttpStatus.OK);
    }

}