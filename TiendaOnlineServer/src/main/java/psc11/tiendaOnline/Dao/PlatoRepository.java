package psc11.tiendaOnline.Dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import psc11.tiendaOnline.DataDomain.Plato;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer>{
    Plato findById(int id);
    Plato findByNombre(String nombre);
    Plato findByPrecio(double precio);
}
