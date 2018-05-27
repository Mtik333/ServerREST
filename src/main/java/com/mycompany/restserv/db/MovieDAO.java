/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.restserv.db;

import com.mycompany.restserv.moviedto.RsiMovie;
import java.util.List;

public interface MovieDAO extends GenericDao<RsiMovie, Integer> {
    List findByMovieTitle(String title);

    List findAllMovies();
}
