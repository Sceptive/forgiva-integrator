package com.sceptive.forgiva.integrator.services.impl.userservices;

import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.GOtp;
import com.sceptive.forgiva.integrator.core.crypto.Asymmetric;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.SecurityManager;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostUser2faDisableRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUser2faEnableRequest;

import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

public class WS2Fa {

    /**
     *
     * Enables 2FA with respective OTP confirmation
     *
     * @param _request
     * @return
     */
    public static Response user_2fa_enable(PostUser2faEnableRequest _request) {

        return SecurityManager.secure_invoke(_request.getHeader(), false, (session) -> {

            OperationResult response = new OperationResult();

            try {

                // If any of the parameters is empty
                // Or 2FA is already set
                if (_request.getSotpCode() == null ||
                        _request.getValidationCode() == null ||
                        _request.getSotpCode().isEmpty() ||
                        _request.getValidationCode().isEmpty() ||
                        WSUserSettings.settings_get_ex(session.user_id,
                                Constants.CONST_US_2FA_ENB).contentEquals("true")
                ) {
                    throw new InvalidValueException("Request contains invalid data");
                }

                byte[] decrypted_sotp = Asymmetric.decrypt_using_session(
                                            Common.decodeHex(_request.getSotpCode()),session);
                byte[] decrypted_valc = Asymmetric.decrypt_using_session(
                                            Common.decodeHex(_request.getValidationCode()),session);
                String sotp  = new String(decrypted_sotp, StandardCharsets.UTF_8);
                String valc  = new String(decrypted_valc, StandardCharsets.UTF_8);

                if (GOtp.gotp_code(sotp).equalsIgnoreCase(valc)) {

                    WSUserSettings.settings_set_ex(session.user_id,
                            Constants.CONST_US_2FA_COD,
                            sotp);

                    response.setInfo("Ok");
                } else {
                    response.error("Invalid value");
                }

            } catch (Exception e) {
                Warning.get_instance().print(e);
                response.error("Could not enable 2FA");
            }

            return Response.ok().entity(response).build();
        });

    }

    /**
     *
     * Disables 2FA with respective OTP confirmation
     *
     * @param _request
     * @return
     */
    public static Response user_2fa_disable(PostUser2faDisableRequest _request) {

        return SecurityManager.secure_invoke(_request.getHeader(), false, (session) -> {

            OperationResult response = new OperationResult();

            try {

                String sotp_code;
                // If any of the parameters is empty
                // Or 2FA is not set
                if (_request.getValidationCode() == null ||
                        _request.getValidationCode().isEmpty() ||
                        (sotp_code = WSUserSettings.settings_get_ex_ex(session.user_id,
                                Constants.CONST_US_2FA_COD)) == null
                        || sotp_code.isEmpty()
                ) {
                    throw new InvalidValueException("Request contains invalid data");
                }

                byte[] decrypted_valc = Asymmetric.decrypt_using_session(
                        Common.decodeHex(_request.getValidationCode()),session);
                String valc  = new String(decrypted_valc, StandardCharsets.UTF_8);

                if (GOtp.gotp_code(sotp_code).equalsIgnoreCase(valc)) {

                    WSUserSettings.settings_set_ex(session.user_id,
                            Constants.CONST_US_2FA_COD,
                            "");

                    response.setInfo("Ok");
                } else {
                    response.error("Invalid validation code");
                }


            } catch (Exception e) {
                Warning.get_instance().print(e);
                response.error("Could not disable 2FA");
            }

            return Response.ok().entity(response).build();
        });

    }
}
