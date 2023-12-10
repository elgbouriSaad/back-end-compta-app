package dot.compta.backend.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExpenseReportStatus {
	
	SAVED(0, "SAVED"),
	VALIDATED(1, "VALIDATED");

	private final int code;
	private final String value;

}
