package com.ikariscraft.cyclecare.api.requests;

import android.os.Parcel;
import android.os.Parcelable;

public class PasswordResetRequest implements Parcelable {
    private String newPassword;
    private String confirmPassword;
    private String token;
    private String email;

    public PasswordResetRequest(String newPassword, String confirmPassword, String token) {
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
        this.token = token;
        this.email = email;
    }
    public PasswordResetRequest() {
    }

    protected PasswordResetRequest(Parcel in) {
        newPassword = in.readString();
        confirmPassword = in.readString();
        token = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newPassword);
        dest.writeString(confirmPassword);
        dest.writeString(token);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PasswordResetRequest> CREATOR = new Creator<PasswordResetRequest>() {
        @Override
        public PasswordResetRequest createFromParcel(Parcel in) {
            return new PasswordResetRequest(in);
        }

        @Override
        public PasswordResetRequest[] newArray(int size) {
            return new PasswordResetRequest[size];
        }
    };

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
