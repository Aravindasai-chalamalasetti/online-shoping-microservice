package com.user.utils;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKey;

public class TokenConstant {
    public static final Integer EXPIRE_OTP = 15;
    public static final String EMAIL_STATUS = "success";
    public static final String EMAIL_STATUS_SUCCESS_MESSAGE = "Otp sent successfully";
    public static final String EMAIL_STATUS_FAILED_MESSAGE = "Email not sent successfully";
    public static final String ADMIN = "admin";

    public static final String UUID = "123e4567-e89b-12d3-a456-426614174000";
    public static final String AUTHORITIES = Base64.getEncoder().encodeToString(
            "your_very_secure_secret_key_string_here_must_be_at_least_256_bits_long".getBytes()
    );
    public static final String AUTHORIZATION = "Authorization";
    public static final String SUBJECT_SIGNUP_OTP = "Verify Your Email Address";
    public static final String IGNORE_SIGNUP_OTP = "If you did not make this request,you can ignore this email and the account will be deleted.";
    public static final String CONTENT_SIGNUP_OTP = "You're almost ready to get started. Please verify your email address using\n" + " the code below.";
    public static final String IMAGE_SIGNUP_OTP = "https://scremerdev.blob.core.windows.net/images/verify_email_crop.png";

    public static final String BUSINESS_IMAGE_FORGOTPASSWORD_OTP = "https://scremerblob.blob.core.windows.net/images/reset-template.png";
    public static final String SUBJECT_FORGOTPASSWORD_OTP = "Reset Your Password";
    public static final String IGNORE_FORGOTPASSWORD_OTP =
            "If you did not make this password request,you can safely ignore this email.";
    public static final String CONTENT_FORGOTPASSWORD_OTP = "We received a request to reset your Scremer password.Please enter the code below to reset your password.";
    public static final String IMAGE_FORGOTPASSWORD_OTP = "https://scremerdev.blob.core.windows.net/images/forgot_password.png";
    public static final String BUSINESS_IMAGE_SIGNUP_OTP = "https://scremerblob.blob.core.windows.net/images/Image.png";
    public static final Integer MAX_FILES_ALLOWED = 3;
    public static final byte DEFAULT_EARNED_REFERRAL = 2;
    public static final byte DEFAULT_UNUSED_REFERRAL = 2;

    public static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final int REFERRAL_CODE_LENGTH = 6;
    public static final SecureRandom RANDON_KEY = new SecureRandom();

    // Generate a secure secret key for JWT
    public static String generateSecureJwtSecret() {
        try {
            SecretKey key = javax.crypto.KeyGenerator.getInstance("HmacSHA256").generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT secret key", e);
        }
    }
}
