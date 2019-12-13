$(function() {
    const $confirmBox = $("#matchingPassword");
    const $submitBtn = $("#submitBtn");
    const $errorMsg =  $('<div class="alert alert-danger" id="errorMsg">Passwords do not match</div>');

    let isValid = false;

    let options = {
        common: {
            minChar: 8,
            maxChar: 64,
            usernameField: "#email",
            debug: true,
            onKeyUp: function (evt, data) {
                const options = $(evt.target).data('pwstrength-bootstrap');

                isValid = options.instances.errors.length === 0;
            }
        },
        rules: {
            activated: {
                wordMaxLength: true,
                wordInvalidChar: true,
                wordSequences: false,
                wordRepetitions: false,
                wordThreeNumbers: false,
                wordOneSpecialChar: false,
                wordTwoSpecialChar: false,
                wordUpperLowerCombo: true,
                wordLetterNumberCharCombo: false
            }
        },
        ui: {
            showVerdictsInsideProgressBar: true,
            showErrors: true,
            showPopover: true,
            popoverPlacement: "right"
        }
    };
    options.i18n = {
        fallback: {
            wordMinLength: 'Your password is too short',
            wordMaxLength: 'Your password is too long',
            wordInvalidChar: 'Your password contains an invalid character',
            wordNotEmail: 'Do not use your email as your password',
            wordSimilarToUsername: 'Your password cannot contain your username',
            wordTwoCharacterClasses: 'Use different character classes',
            wordRepetitions: 'Too many repetitions',
            wordSequences: 'Your password contains sequences',
            errorList: 'Errors:',
            veryWeak: 'Very Weak',
            weak: 'Weak',
            normal: 'Normal',
            medium: 'Medium',
            strong: 'Strong',
            veryStrong: 'Very Strong',
            wordNoUppercase: 'Your password contains no uppercase character',
            wordNoLowercase: 'Your password contains no lowercase character',
            wordNoNumber: 'Your password contains no numbers'
        },
        t: function (key) {
            let result = options.i18n.fallback[key];

            return result === undefined ? '' : result;
        }
    };
    const $passwordBox = $("#password").pwstrength(options);

    $passwordBox.pwstrength("addRule", "wordNoUppercase", function (options, word, score) {
        if(/^[^A-Z]+$/.test(word)) {
            return score;
        }
        return 0;
    }, -50, true);
    $passwordBox.pwstrength("addRule", "wordNoLowercase", function (options, word, score) {
        if(/^[^a-z]+$/.test(word)) {
            return score;
        }
        return 0;
    }, -50, true);
    $passwordBox.pwstrength("addRule", "wordNoNumber", function (options, word, score) {
        if(/^[^0-9]+$/.test(word)) {
            return score;
        }
        return 0;
    }, -50, true);

    $submitBtn.attr("disabled", true);
    $errorMsg.insertAfter($confirmBox);

    function checkMatchingPasswords(){
        if($confirmBox.val() !== "" && $passwordBox.val !== ""){
            if($confirmBox.val() === $passwordBox.val() && isValid){
                $submitBtn.removeAttr("disabled");
                $errorMsg.remove();
            } else {
                $submitBtn.attr("disabled", true);
                $errorMsg.insertAfter($confirmBox);
            }
        }
    }

    $passwordBox.keyup(checkMatchingPasswords);
    $confirmBox.keyup(checkMatchingPasswords);
});
