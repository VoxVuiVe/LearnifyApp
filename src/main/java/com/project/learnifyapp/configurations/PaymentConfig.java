package com.project.learnifyapp.configurations;


import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class PaymentConfig {

    public static String vnp_PayUrl = "";
    public static String vnp_ReturnUrl = "";
    public static String vnp_TmnCode = "";
    public static String secretKey = "";

    public static String vnp_Version = "";
    public static String vpn_Command = "";
    public static String orderType = "";
    public static String vnp_ApiUrl = "";

    public static String md5(String message) {
        String digest = null;
        try {
            // Khởi tạo một đối tượng MessageDigest với thuật toán MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Mã hóa chuỗi đầu vào thành một mảng byte
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            // Tạo một StringBuilder để xây dựng chuỗi mã hóa
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                // Chuyển đổi mỗi byte thành một chuỗi hex và thêm vào StringBuilder
                sb.append(String.format("%02x", b & 0xff));
            }
            // Chuyển StringBuilder thành chuỗi
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    public static String Sha256(String message) {
        String digest = null;
        try {
            // Khởi tạo một đối tượng MessageDigest với thuật toán SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Mã hóa chuỗi đầu vào thành một mảng byte
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            // Tạo một StringBuilder để xây dựng chuỗi mã hóa
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                // Chuyển đổi mỗi byte thành một chuỗi hex và thêm vào StringBuilder
                sb.append(String.format("%02x", b & 0xff));
            }
            // Chuyển StringBuilder thành chuỗi
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    public static String hashAllFields(Map fields) {
        // Tạo một danh sách chứa tất cả các tên trường trong Map
        List fieldNames = new ArrayList<>(fields.keySet());
        // Sắp xếp danh sách theo thứ tự tăng dần
        Collections.sort(fieldNames);
        // Tạo một StringBuilder để xây dựng chuỗi băm
        StringBuilder sb = new StringBuilder();
        // Duyệt qua tất cả các tên trường trong danh sách
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            // Lấy tên trường hiện tại
            String fieldName = (String) itr.next();
            // Lấy giá trị của trường hiện tại
            String fieldValue = (String) fields.get(fieldName);
            // Nếu giá trị trường không rỗng, thêm tên trường và giá trị vào StringBuilder
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            // Nếu đây không phải là trường cuối cùng, thêm một dấu "&" vào StringBuilder
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        // Tạo chữ ký số từ chuỗi băm
        return hmacSHA512(secretKey,sb.toString());
    }

    public static String hmacSHA512(final String key, final String data) {
        try {
            // Kiểm tra xem khóa và dữ liệu có rỗng không
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            // Khởi tạo một đối tượng Mac với thuật toán HmacSHA512
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            // Chuyển đổi khóa thành một mảng byte
            byte[] hmacKeyBytes = key.getBytes();
            // Tạo một SecretKeySpec từ mảng byte
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            // Khởi tạo Mac với SecretKeySpec
            hmac512.init(secretKey);
            // Chuyển đổi dữ liệu thành một mảng byte
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            // Tạo chữ ký số từ dữ liệu
            byte[] result = hmac512.doFinal(dataBytes);
            // Tạo một StringBuilder để xây dựng chuỗi chữ ký số
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                // Chuyển đổi mỗi byte thành một chuỗi hex và thêm vào StringBuilder
                sb.append(String.format("%02x", b & 0xff));
            }
            // Chuyển StringBuilder thành chuỗi
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            // Lấy địa chỉ IP từ tiêu đề "X-FORWARDED-FOR" của yêu cầu
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            // Nếu không tìm thấy địa chỉ IP, lấy địa chỉ IP từ yêu cầu
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    public static String getRandomNumber(int len) {
        // Khởi tạo một đối tượng Random
        Random rnd = new Random();
        // Tạo một chuỗi chứa các số từ 0 đến 9
        String chars = "0123456789";
        // Tạo một StringBuilder để xây dựng số ngẫu nhiên
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            // Thêm một số ngẫu nhiên từ chuỗi vào StringBuilder
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        // Chuyển StringBuilder thành chuỗi
        return sb.toString();
    }
}
