package com.haulmont.testtask.ui;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.AbstractField;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class CustomValidator {
    public static ValidationResult addValidator(@NotNull AbstractField field, Validator validator) {
        AtomicReference<ValidationResult> result = null;
        field.addValueChangeListener(event -> {
            result.set(validator.apply(event.getValue(), new ValueContext(field)));

        });
        return result.get();
    }
}
