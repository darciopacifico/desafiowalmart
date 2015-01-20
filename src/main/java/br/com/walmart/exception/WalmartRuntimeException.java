package br.com.walmart.exception;


/**
 * Erro runtime do projeto. Representa um erro que não é passível de tratamento. Não deve ser esperado pelos componentes clientes.
 * 
 * @author darcio
 *
 */
public class WalmartRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2193957498849778735L;

	public WalmartRuntimeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WalmartRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public WalmartRuntimeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WalmartRuntimeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WalmartRuntimeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	
}
