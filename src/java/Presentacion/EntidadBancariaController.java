package Presentacion;

import Datos.EntidadBancariaDAO;
import Negocio.EntidadBancaria;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntidadBancariaController {

    @Autowired
    EntidadBancariaDAO entidadBancariaDAO;
    //Estas son las dos entidades creadas con Autowired , se hace con autowired applicationContext
    // estas son las dos entidades entidadBancaria que he comentado en la clase delete y read

    @RequestMapping(value = {"/EntidadBancaria/{idEntidad}"}, method = RequestMethod.GET)
    public void read(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, @PathVariable("idEntidad") int idEntidad) throws IOException {

        try {
            //EntidadBancaria entidadBancaria = new EntidadBancariaDAOImpHibernate().read(idEntidad);
            EntidadBancaria entidadBancaria = entidadBancariaDAO.read(idEntidad); //creo variable para pasarla abajo

            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(entidadBancaria); //Aqui la variable creada
            httpServletResponse.getWriter().println(json);

        } catch (Exception ex) {
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                //Capturamos el error de si da error mostrar el error !!! LOL
            }
        }
    }

    @RequestMapping(value = {"/EntidadBancaria/{idEntidad}"}, method = RequestMethod.DELETE)
    public void delete(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, @PathVariable("idEntidad") int idEntidad) {

        try {
            //EntidadBancariaDAOImpHibernate entidadBancariaDAO = new EntidadBancariaDAOImpHibernate(); 
            entidadBancariaDAO.delete(idEntidad);
            /*ObjectMapper objectMapper = new ObjectMapper();
             String json = objectMapper.writeValueAsString(null);
             httpServletResponse.setContentType("application/json; charset=UTF-8");
             httpServletResponse.getWriter().println(json);*/
            httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception ex) {
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                //Capturamos el error de si da error mostrar el error !!! LOL
            }
        }
    }

    @RequestMapping(value = {"/EntidadBancaria"}, method = RequestMethod.GET)
    public void find(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse/*, @PathVariable("idEntidad") int idEntidad*/) throws IOException {

        try {
            //EntidadBancaria entidadBancaria = new EntidadBancariaDAOImpHibernate().read(idEntidad);
            List<EntidadBancaria> entidadBancaria = entidadBancariaDAO.findAll(); //creo variable para pasarla abajo

            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(entidadBancaria); //Aqui la variable creada
            httpServletResponse.getWriter().println(json);

        } catch (Exception ex) {
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                //Capturamos el error de si da error mostrar el error !!! LOL
            }
        }
    }

    @RequestMapping(value = {"/EntidadBancaria"}, method = RequestMethod.POST)
    public void insert(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, /*@PathVariable("idEntidad") int idEntidad,*/ @RequestBody String json) {

        try {
            //Aqui transformo el json de la cabecera en un objeto java para poder insertarlo en la BBDD
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            EntidadBancaria entidadBancaria = (EntidadBancaria) objectMapper.readValue(json, EntidadBancaria.class);

            entidadBancariaDAO.insert(entidadBancaria);  //Inserto entidad

            //Casteo el objeto creado de nuevo a formato json para poder devolverlo
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            json = objectMapper.writeValueAsString(entidadBancaria); //Aqui la variable creada
            httpServletResponse.getWriter().println(json);


            //----Prueba Errores de JAVAX

        } catch (ConstraintViolationException cve) {
            for (ConstraintViolation constraintViolation : cve.getConstraintViolations()) {
                System.out.println("En el campo '" + constraintViolation.getPropertyPath() + "':" + constraintViolation.getMessage());
            }
            
            /*----------------------*/


        } catch (Exception ex) {
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                //Capturamos el error de si da error mostrar el error !!! LOL
            }
        }
    }

    @RequestMapping(value = {"/EntidadBancaria/{idEntidad}"}, method = RequestMethod.PUT)
    public void update(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, @PathVariable("idEntidad") int idEntidad, @RequestBody String json) {

        try {
            //Aqui transformo el json de la cabecera en un objeto java para poder insertarlo en la BBDD
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            EntidadBancaria entidadBancaria = (EntidadBancaria) objectMapper.readValue(json, EntidadBancaria.class);

            //Leo la entidad que voy a actualizar y la guardo en un objeto
            EntidadBancaria entidadBancariaUpdate = entidadBancariaDAO.read(idEntidad);

            //Cambio los valores que ya estan guardados por los que me han pasado en le json
            entidadBancariaUpdate.setNombre(entidadBancaria.getNombre());
            entidadBancariaUpdate.setCif(entidadBancaria.getCif());
            entidadBancariaUpdate.setCodigoEntidad(entidadBancaria.getCodigoEntidad());
            entidadBancariaUpdate.setTipoEntidadBancaria(entidadBancaria.getTipoEntidadBancaria());


            entidadBancariaDAO.update(entidadBancariaUpdate);  //Actualizo la entidad


            //Casteo el objeto creado de nuevo a formato json para poder devolverlo
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            json = objectMapper.writeValueAsString(entidadBancariaUpdate); //Aqui la variable creada
            httpServletResponse.getWriter().println(json);

        } catch (Exception ex) {
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                //Capturamos el error de si da error mostrar el error !!! LOL
            }
        }
    }
}