package com.megahype.MegaHype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
public class FormController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/submit_form")
    public ResponseEntity<?> submitForm(@Valid @RequestBody FormData formData) {
        // Enviar e-mail
        sendEmail(formData);

        return ResponseEntity.ok().body("{\"message\": \"Dados enviados com sucesso!\"}");
    }

    // Método para enviar e-mail
    private void sendEmail(FormData formData) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("megahypebusiness@gmail.com"); // E-mail onde você deseja receber as mensagens

        // Prevenção contra valores nulos
        String tipoServico = formData.getTipoServico() != null ? formData.getTipoServico() : "Tipo de serviço não informado";
        String nome = formData.getName() != null ? formData.getName() : "Nome não informado";
        String telefone = formData.getPhone() != null ? formData.getPhone() : "Telefone não informado";
        String mensagem = formData.getMessage() != null ? formData.getMessage() : "Mensagem não informada";
        String emailUsuario = formData.getEmail() != null ? formData.getEmail() : "Email não informado";

        message.setSubject("Novo Pedido: " + tipoServico);
        message.setText("Nome: " + nome +
                "\nEmail: " + emailUsuario +
                "\nTelefone: " + telefone +
                "\nMensagem: " + mensagem);

        mailSender.send(message);
    }

    public static class FormData {
        @NotNull(message = "O nome é obrigatório")
        @Size(max = 20, message = "O nome não pode exceder 20 caracteres")
        private String name;

        @NotNull(message = "O tipo de serviço é obrigatório")
        private String tipoServico;

        @NotNull(message = "A mensagem é obrigatória")
        @Size(max = 500, message = "A mensagem não pode exceder 500 caracteres")
        private String message;

        @NotNull(message = "O telefone é obrigatório")
        @Size(max = 15, message = "O telefone não pode exceder 15 caracteres")
        private String phone;

        @NotNull(message = "O email é obrigatório")
        @Email(message = "Email deve ser válido")
        private String email;

        // Constructor
        public FormData(String name, String tipoServico, String message, String phone, String email) {
            this.name = name;
            this.tipoServico = tipoServico;
            this.message = message;
            this.phone = phone;
            this.email = email;
        }

        // Getters e Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTipoServico() {
            return tipoServico;
        }

        public void setTipoServico(String tipoServico) {
            this.tipoServico = tipoServico;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
