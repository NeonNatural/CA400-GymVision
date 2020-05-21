package com.example.gymvision;

import org.junit.Test;

import com.example.gymvision.utils.EmailUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class EmailFormatValidationTest {

    @Test
    public void email_isValid() {
        String email = "test@valid.email.com";

        boolean isValid = EmailUtils.validateEmail(email);

        assertTrue(isValid);
    }

    @Test
    public void email_missingAt_notValid() {
        String email = "test.valid.email.com";

        boolean isValid = EmailUtils.validateEmail(email);

        assertFalse(isValid);
    }

}
