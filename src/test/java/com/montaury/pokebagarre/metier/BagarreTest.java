package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurMemePokemon;
import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.erreurs.ErreurRecuperationPokemon;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



class BagarreTest {

    PokeBuildApi fausseApi;
    Bagarre bagarre;

    @BeforeEach
    void preparer () {
        fausseApi = mock(PokeBuildApi.class) ;
        bagarre = new Bagarre(fausseApi);
    }
    @Test
    void devrait_lever_une_erreur_si_le_1er_pokemon_est_null ()
    {
        // Given
        Throwable erreur = Assertions.catchThrowable(() -> bagarre.demarrer(null, "pikachu"));

        // THEN
        assertThat(erreur).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void devrait_lever_une_erreur_si_le_2eme_pokemon_est_null ()
    {
        // Given
        Throwable erreur = Assertions.catchThrowable(() -> bagarre.demarrer("pikachu", null));

        // THEN
        assertThat(erreur).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void devrait_lever_une_erreur_si_le_1er_pokemon_est_vide ()
    {
        // Given
        Throwable erreur = Assertions.catchThrowable(() -> bagarre.demarrer("", "pikachu"));

        // THEN
        assertThat(erreur).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void devrait_lever_une_erreur_si_le_2eme_pokemon_est_vide ()
    {
        // Given
        Throwable erreur = Assertions.catchThrowable(() -> bagarre.demarrer("pikachu", ""));

        // THEN
        assertThat(erreur).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void devrait_lever_une_erreur_si_les_deux_pokemons_sont_identiques ()
    {
        // Given
        Throwable erreur = Assertions.catchThrowable(() -> bagarre.demarrer("pikachu", "pikachu"));

        // THEN
        assertThat(erreur).isInstanceOf(ErreurMemePokemon.class).hasMessage("Impossible de faire se bagarrer un pokemon avec lui-meme");
    }

    @Test
    void devrait_echouer_si_erreur_api_deuxieme_pokemon ()
    {
        // Given
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url_pikachu",new Stats(1, 2))));
        Mockito.when(fausseApi.recupererParNom("scarabrute"))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("scarabrute")));
        // When
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer("pikachu", "scarabrute");

        // Then
        assertThat(futurVainqueur)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'scarabrute'");
    }

    @Test
    void devrait_echouer_si_erreur_api_premier_pokemon ()
    {
        // Given
        Mockito.when(fausseApi.recupererParNom("scarabrute"))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon("scarabrute")));
        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url_pikachu",new Stats(1, 2))));
        // When
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer("scarabrute", "pikachu");

        // Then
        assertThat(futurVainqueur)
                .failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur 'scarabrute'");
    }

    @Test
    void devrait_retourner_premier_pokemon_s_il_est_gagnant ()
    {
        // Given
        Mockito.when(fausseApi.recupererParNom("scarabrute"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("scarabrute", "url_scarabrute",new Stats(4, 2))));

        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(new Pokemon("pikachu", "url_pikachu",new Stats(1, 2))));
        // When
        CompletableFuture<Pokemon> futurVainqueur = bagarre.demarrer("scarabrute", "pikachu");
        // Then
        assertThat(futurVainqueur)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon -> {
                    assertThat(pokemon.getNom()).isEqualTo("scarabrute");
                });
    }
}