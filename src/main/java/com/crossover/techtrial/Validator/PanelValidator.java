package com.crossover.techtrial.Validator;

import com.crossover.techtrial.model.Panel;
import org.springframework.stereotype.Component;

@Component
public class PanelValidator {

    public boolean validate(Panel panel)  {
        if (panel.getSerial().length() < 16) {
            return false;
        }
        if (!isNumberWith6Decimals(panel.getLatitude())) {
            return false;
        }
        if (!isNumberWith6Decimals(panel.getLongitude())) {
            return false;
        }
        return true;
    }

    public boolean isNumberWith6Decimals(Double loc) {
        String string = String.valueOf(loc);
        return string.matches("^\\d+\\.\\d{6}$");
    }
}
