package ru.otus.hw12.web.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import ru.otus.hw12.api.model.User;
import ru.otus.hw12.api.service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UsersApiServlet extends HttpServlet {
    private static final int ID_PATH_PARAM_POSITION = 1;

    private final UserService userService;
    private final Gson gson;

    public UsersApiServlet(UserService userService, Gson gson) {
        this.userService = userService;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long userId = extractIdFromRequest(request);
        String data;
        if (userId != -1) {
            // exact user
            data = userToJsonObject(userService.getUser(userId).orElse(null)).toString();
        } else {
            // all users
            data = usersToJsonArray().toString();
        }
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(data);
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

    private JsonObject userToJsonObject(User user) {
        JsonObject userObject = new JsonObject();
        if (user!=null) {
            userObject.add("id", new JsonPrimitive(user.getId()));
            userObject.add("name", new JsonPrimitive(user.getName()));
            userObject.add("login", new JsonPrimitive(user.getLogin()));
            userObject.add("password", new JsonPrimitive("******"));
        }

        return userObject;
    }

    private JsonArray usersToJsonArray() {
        JsonArray jsonArray = new JsonArray();
        userService.getAll().forEach(user -> {
            jsonArray.add(userToJsonObject(user));
        });
        return jsonArray;
    }

}
