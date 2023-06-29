package org.apache.beam.examples;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


import org.apache.beam.examples.ExpresoSantiago;

public class JsonUrlReader2 {

    public JsonUrlReader2() {

    }

    public static void main(String[] args) throws DatabindException, MalformedURLException, IOException {
        JsonUrlReader2 j = new JsonUrlReader2();
        ArrayList<ExpresoSantiago> Atp = j.cargarURLx();

        for (ExpresoSantiago tp : Atp) {
            System.out.println(tp);
        }
    }

    public ArrayList<ExpresoSantiago> cargarURLx() throws StreamReadException, DatabindException, MalformedURLException, IOException {
        String url = "https://www.red.cl/restservice_v2/rest/conocerecorrido?codsint=101"; //API EXPRESO DE SANTIAGO
        ArrayList<ExpresoSantiago> Aes = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode elementos = mapper.readTree(new URL(url));
        JsonNode negocio = elementos.get("negocio");
        JsonNode ida = elementos.get("ida");
        JsonNode regreso = elementos.get("regreso");
        JsonNode ida_horario = ida.get("horarios");
        JsonNode regreso_horario = regreso.get("horarios");
        //AGREGANDO DATOS IDA
        if (ida_horario.isArray()) {
            for (JsonNode horario : ida_horario) {
                JsonNode paradero_ida = ida.get("paraderos");

                for (JsonNode paradero : paradero_ida) {
                    ExpresoSantiago es = new ExpresoSantiago();
                    es.setId_empresa(negocio.get("id").asText());
                    es.setNombre_empresa(negocio.get("nombre").asText());
                    es.setId_ida(ida.get("id").asText());
                    es.setTipo_dia(horario.get("tipoDia").asText());
                    es.setInicio_dia(horario.get("inicio").asText());
                    es.setFin_dia(horario.get("fin").asText());
                    es.setDestino(ida.get("destino").asText());
                    es.setParadero(paradero.get("name").asText());
                    es.setCod_paradero(paradero.get("cod").asText());
                    es.setComuna_paradero(paradero.get("comuna").asText());
                    Aes.add(es);
                }

                

            }
        }
        //AGREGANDO DATOS VUELTA
        if (regreso_horario.isArray()) {
            for (JsonNode horario : regreso_horario) {
                JsonNode paradero_regreso = regreso.get("paraderos"); 
           
                for (JsonNode paradero : paradero_regreso) {
                    ExpresoSantiago es = new ExpresoSantiago();
                    es.setId_empresa(negocio.get("id").asText());
                    es.setNombre_empresa(negocio.get("nombre").asText());
                    es.setId_ida(regreso.get("id").asText());
                    es.setTipo_dia(horario.get("tipoDia").asText());
                    es.setInicio_dia(horario.get("inicio").asText());
                    es.setFin_dia(horario.get("fin").asText());
                    es.setDestino(regreso.get("destino").asText());
                    es.setParadero(paradero.get("name").asText());
                    es.setCod_paradero(paradero.get("cod").asText());
                    es.setComuna_paradero(paradero.get("comuna").asText());
                    Aes.add(es);
                }

                

            }
        }

        return Aes;
    }
}
