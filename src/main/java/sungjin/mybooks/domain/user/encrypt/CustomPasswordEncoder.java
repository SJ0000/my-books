package sungjin.mybooks.domain.user.encrypt;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CustomPasswordEncoder implements PasswordEncoder{

    private static final String ALGORITHM = "SHA-256";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private final String salt;
    private final int keyStretchingCount;

    public CustomPasswordEncoder(PasswordEncoderProperties properties) {
        this.salt = properties.getSalt();
        this.keyStretchingCount = properties.getKeyStretchCount();
    }

    @Override
    public String encode(String text){
        String saltedText = text+salt;
        byte[] bytes = encrypt(saltedText.getBytes(CHARSET));
        return bytesToHexString(bytes);
    }

    private MessageDigest createDigest(){
        try{
            return MessageDigest.getInstance(ALGORITHM);
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("PasswordEncoder 생성에 필요한 암호화 알고리즘을 찾을 수 없습니다. ALGORITHM = " + ALGORITHM);
        }
    }

    private byte[] encrypt(byte[] bytes){
        MessageDigest digest = createDigest();

        for(int i=0;i<keyStretchingCount;i++){
            bytes = digest.digest(bytes);
        }
        digest.reset();
        return bytes;
    }

    private String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }
}
