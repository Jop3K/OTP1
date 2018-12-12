package models;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public interface IPasswordSecurity {

    public String get_SHA_256_SecurePassword(String password, byte[] salt);

    public byte[] generateSalt() throws NoSuchAlgorithmException, NoSuchProviderException;

}
