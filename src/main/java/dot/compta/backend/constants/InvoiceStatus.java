package dot.compta.backend.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InvoiceStatus {
	
	SAVED(0, "SAVED"),
	VALIDATED(1, "VALIDATED"),
	PAYED(2, "PAYED");

	private final int code;
	private final String value;

}
