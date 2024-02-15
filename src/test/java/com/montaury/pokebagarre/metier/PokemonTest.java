package com.montaury.pokebagarre.metier;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {
    @Test
    public void devrait_etre_vainqueur_s_il_a_une_meilleure_attaque ()
    {
        // GIVEN
        Pokemon premier  = new Pokemon("Pikachu", "urlImage", new Stats(12, 12));
        Pokemon second  = new Pokemon("Rondoudou", "urlImage", new Stats(10, 12));
        // WHEN
        boolean estVainqueur = premier.estVainqueurContre(second);
        // THEN
        assertThat(estVainqueur).isTrue() ;
    }

    @Test
    public void devrait_etre_vainqueur_s_il_a_une_meilleure_defense ()
    {
        // GIVEN
        Pokemon premier  = new Pokemon("Pikachu", "urlImage", new Stats(12, 12));
        Pokemon second  = new Pokemon("Rondoudou", "urlImage", new Stats(12, 10));
        // WHEN
        boolean estVainqueur = premier.estVainqueurContre(second);
        // THEN
        assertThat(estVainqueur).isTrue() ;
    }

    @Test
    public void devrait_etre_vainqueur_s_il_a_la_priorite ()
    {
        // GIVEN
        Pokemon premier  = new Pokemon("Pikachu", "urlImage", new Stats(12, 12));
        Pokemon second  = new Pokemon("Rondoudou", "urlImage", new Stats(12, 12));
        // WHEN
        boolean estVainqueur = premier.estVainqueurContre(second);
        // THEN
        assertThat(estVainqueur).isTrue() ;
    }

    @Test
    public void devrait_etre_perdant_s_il_a_une_moins_bonne_attaque ()
    {
        // GIVEN
        Pokemon premier  = new Pokemon("Pikachu", "urlImage", new Stats(10, 12));
        Pokemon second  = new Pokemon("Rondoudou", "urlImage", new Stats(12, 12));
        // WHEN
        boolean estVainqueur = premier.estVainqueurContre(second);
        // THEN
        assertThat(estVainqueur).isFalse() ;
    }

    @Test
    public void devrait_etre_perdant_s_il_a_une_moins_bonne_defense ()
    {
        // GIVEN
        Pokemon premier  = new Pokemon("Pikachu", "urlImage", new Stats(12, 10));
        Pokemon second  = new Pokemon("Rondoudou", "urlImage", new Stats(12, 12));
        // WHEN
        boolean estVainqueur = premier.estVainqueurContre(second);
        // THEN
        assertThat(estVainqueur).isFalse() ;
    }

}