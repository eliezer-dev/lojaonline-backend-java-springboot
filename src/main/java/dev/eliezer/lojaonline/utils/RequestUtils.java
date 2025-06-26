package dev.eliezer.lojaonline.utils;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static Boolean isNormalUser(HttpServletRequest request) {
        if (request.getAttribute("user_role") == null) {
            throw new BusinessException("user_role is missing.");
        }
        if (Long.valueOf(request.getAttribute("user_role").toString()) == 2) {
            return true;
        }
        return false;
    }

    public static Boolean isClientUser(HttpServletRequest request) {
        if (request.getAttribute("user_role") == null) {
            throw new BusinessException("user_role is missing.");
        }

        if (Long.valueOf(request.getAttribute("user_role").toString()) == 1) {
            return true;
        }
        return false;
    }

    public static Boolean isUserAdmin(HttpServletRequest request) {
        if (request.getAttribute("user_role") == null) {
            throw new BusinessException("user_role is missing.");
        }

        if (Long.valueOf(request.getAttribute("user_role").toString()) == 0) {
            return true;
        }
        return false;
    }

    public static Long extractUserIdOfRequest (HttpServletRequest request) {
        if (Long.valueOf(request.getAttribute("user_id").toString()) == null) {
            throw new BusinessException("user_role is missing.");
        }
        return Long.valueOf(request.getAttribute("user_id").toString());
    }
}
