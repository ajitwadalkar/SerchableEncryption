import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class EncDecFunction {


    //KeyGen Method
    static String  keyGen(int lambda) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(lambda,secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytes = secretKey.getEncoded();
        return DatatypeConverter.printHexBinary(bytes);

    }

    //to get the key from text file
    private static SecretKeySpec getSecretKey(String fileName){
        String encodedKey = FileReadWrite.ReadFile(fileName).get(0);
        byte[] bytes = DatatypeConverter.parseHexBinary(encodedKey);
        return new SecretKeySpec(bytes, "AES");
    }

    //CBC Encryption
    static byte[] encryptCBC(String plainText, String fileName) throws Exception {
        byte[] inputText = plainText.getBytes();

        // Generating IV.
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        //Fetching the key
        SecretKeySpec secretKey = getSecretKey(fileName);

        // Encrypt.
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(inputText);

        // Combine IV and encrypted part.
        byte[] cipherWithIVText = new byte[ivSize + encrypted.length];
        System.arraycopy(iv, 0, cipherWithIVText, 0, ivSize);
        System.arraycopy(encrypted, 0, cipherWithIVText, ivSize, encrypted.length);

        return cipherWithIVText;
    }

    //CBC Decryption
    static String decryptCBC(byte[] cipherWithIVText, String fileName) {
        int ivSize = 16;

        // Extract IV.
        byte[] iv = new byte[ivSize];
        System.arraycopy(cipherWithIVText, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extract Cipher.
        int cipherSize = cipherWithIVText.length - ivSize;
        byte[] cipherBytes = new byte[cipherSize];
        System.arraycopy(cipherWithIVText, ivSize, cipherBytes, 0, cipherSize);

        //Fetching the key
        SecretKeySpec secretKey = getSecretKey(fileName);

        // Decrypt.
        try {
            Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] decrypted = cipherDecrypt.doFinal(cipherBytes);
            return new String(decrypted);
        }catch (Exception e){
            return  "There was some error decrypting the file. Please check if the used key is correct";
        }



    }

    //ECB Encryption
    static byte[] encryptECB(String plainText, String fileName) throws Exception {
        //Fetching the key
        SecretKeySpec secretKey = getSecretKey(fileName);
        //encrypt
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(plainText.getBytes());
    }

    public static String decryptECB(byte[] cipherText, String fileName) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //Fetching the key
        SecretKeySpec secretKey = getSecretKey(fileName);
        //Decrypt
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(cipherText));
    }


}
