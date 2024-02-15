package com.montaury.pokebagarre.metier;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {
    @Test
    public void pokemon1_devrait_etre_vainqueur_s_il_a_une_meilleure_attaque ()
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
    public void pokemon1_devrait_etre_vainqueur_s_il_a_une_meilleure_defense ()
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
    public void pokemon1_devrait_etre_vainqueur_s_il_a_la_priorite ()
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
    public void pokemon2_devrait_etre_vainqueur_s_il_a_une_meilleure_attaque ()
    {
        // GIVEN
        Pokemon premier  = new Pokemon("Pikachu", "urlImage", new Stats(10, 12));
        Pokemon second  = new Pokemon("Rondoudou", "urlImage", new Stats(12, 12));
        // WHEN
        boolean estVainqueur = second.estVainqueurContre(premier);
        // THEN
        assertThat(estVainqueur).isTrue() ;
    }

    @Test
    public void pokemon2_devrait_etre_vainqueur_s_il_a_une_meilleure_defense ()
    {
        // GIVEN
        Pokemon premier  = new Pokemon("Pikachu", "urlImage", new Stats(12, 10));
        Pokemon second  = new Pokemon("Rondoudou", "urlImage", new Stats(12, 12));
        // WHEN
        boolean estVainqueur = second.estVainqueurContre(premier);
        // THEN
        assertThat(estVainqueur).isTrue() ;
    }

}