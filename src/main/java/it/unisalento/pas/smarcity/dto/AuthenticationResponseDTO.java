package it.unisalento.pas.smarcity.dto;

public class AuthenticationResponseDTO {

    private String jwt;

    private String id;

    public AuthenticationResponseDTO(String jwt, String id) {
        this.jwt = jwt;
        this.id = id;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
