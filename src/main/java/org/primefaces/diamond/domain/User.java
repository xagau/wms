package org.primefaces.diamond.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("User")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2023-01-13T19:12:30.712-05:00[America/Toronto]")
public class User   {
    public static final int USER_STATUS_EMAIL_UNVERIFIED = 0;
    public static final int USER_STATUS_EMAIL_VERIFIED = 1;
    public static final int USER_STATUS_PASSWORD_RESET = 2;
    public static final int USER_STATUS_MFA_SET = 3;
    public static final int USER_STATUS_MFA_RESET = 4;
    public static final int USER_STATUS_FROZEN = 5;
    public static final int USER_STATUS_KYC_STARTED = 6;
    public static final int USER_STATUS_KYC_DONE = 7;
    public static final int USER_STATUS_STALE = 8;
    public static final int USER_STATUS_GOOD_STANDING = 9; // Email verified, password changed, profile filled out, KYC done, MFA enabled


    private @Valid Integer id = 0;
    private @Valid String username = "";
    private @Valid String firstName = "";
    private @Valid String lastName = "";
    private @Valid String email = "";
    private @Valid String password = "";
    private @Valid String phone = "";
    private @Valid Integer userStatus = USER_STATUS_EMAIL_UNVERIFIED;

    /**
     **/
    public User id(Integer id) {
        this.id = id;
        return this;
    }


    @ApiModelProperty(example = "10", value = "")
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     **/
    public User username(String username) {
        this.username = username;
        return this;
    }


    @ApiModelProperty(example = "theUser", value = "")
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     **/
    public User firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }


    @ApiModelProperty(example = "John", value = "")
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     **/
    public User lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    @ApiModelProperty(example = "Smith", value = "")
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     **/
    public User email(String email) {
        this.email = email;
        return this;
    }


    @ApiModelProperty(example = "john@email.com", value = "")
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     **/
    public User password(String password) {
        this.password = password;
        return this;
    }


    @ApiModelProperty(example = "123456789abC", value = "")
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     **/
    public User phone(String phone) {
        this.phone = phone;
        return this;
    }


    @ApiModelProperty(example = "14168785282", value = "")
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * User Status
     **/
    public User userStatus(Integer userStatus) {
        this.userStatus = userStatus;
        return this;
    }


    @ApiModelProperty(example = "1", value = "User Status")
    @JsonProperty("userStatus")
    public Integer getUserStatus() {
        return userStatus;
    }

    @JsonProperty("userStatus")
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(this.id, user.id) &&
                Objects.equals(this.username, user.username) &&
                Objects.equals(this.firstName, user.firstName) &&
                Objects.equals(this.lastName, user.lastName) &&
                Objects.equals(this.email, user.email) &&
                Objects.equals(this.password, user.password) &&
                Objects.equals(this.phone, user.phone) &&
                Objects.equals(this.userStatus, user.userStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, email, password, phone, userStatus);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    username: ").append(toIndentedString(username)).append("\n");
        sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
        sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
        sb.append("    userStatus: ").append(toIndentedString(userStatus)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


}

