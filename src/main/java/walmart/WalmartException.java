package walmart;

/**
 * Erro de checked do projeto
 * @author darcio
 *
 */
public class WalmartException extends Exception {

	private static final long serialVersionUID = -5072420509928879160L;

	
	public WalmartException() {
		super();
	}

	public WalmartException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WalmartException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WalmartException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WalmartException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	
}
