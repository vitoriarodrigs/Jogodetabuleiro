module com.example.jogo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires java.desktop;

    opens com.example.jogo to javafx.fxml;
    opens com.example.jogo.tabuleiro to javafx.fxml;
    exports com.example.jogo;
    exports com.example.jogo.tabuleiro;
    opens  com.example.jogo.jogadores to javafx.fxml;
    exports com.example.jogo.jogadores;
}