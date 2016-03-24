package co.com.maocq.util.error;

import java.util.Map;

public class ErrorResponse {
	private boolean error;
	private Map<String, String> errores;

	public ErrorResponse(boolean error, Map<String, String> errores) {
		this.error = error;
		this.errores = errores;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Map<String, String> getErrores() {
		return errores;
	}

	public void setErrores(Map<String, String> errores) {
		this.errores = errores;
	}

}
