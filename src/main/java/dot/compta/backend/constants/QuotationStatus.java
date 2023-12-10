package dot.compta.backend.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuotationStatus {

	SAVED(0, "SAVED"),
	VALIDATED(1, "VALIDATED"),
	TRANSFORMED(2, "TRANSFORMED");

	private final int code;
	private final String value;

}
