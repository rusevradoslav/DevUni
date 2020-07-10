package app.validations;

import app.models.binding.UserRegisterBindingModel;
import app.validations.anotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    String message;

    @Override
    public void initialize(final PasswordMatches passwordMatches) {
        this.message = passwordMatches.message();
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) obj;
        boolean isValid = userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword());


        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("password").
                    addConstraintViolation();

        }
        return isValid;


    }

}