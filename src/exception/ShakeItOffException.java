package exception;


public class ShakeItOffException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ShakeItOffException(String mensagem) {
        super(mensagem);
    }
}
