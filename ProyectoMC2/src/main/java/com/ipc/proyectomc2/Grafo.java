package com.ipc.proyectomc2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {
    private Map<String, Vertice> vertices;
    private List<Arista> aristas;

    public Grafo() {
        this.vertices = new HashMap<>();
        this.aristas = new ArrayList<>();
    }

    public void agregarVertice(Vertice vertice) {
        vertices.put(vertice.getId(), vertice);
    }

    public void agregarArista(Arista arista) {
        aristas.add(arista);
    }

    public Vertice obtenerVertice(String id) {
        return vertices.get(id);
    }

    public List<Vertice> obtenerVertices() {
        return new ArrayList<>(vertices.values());
    }

    public List<Arista> obtenerAristas() {
        return aristas;
    }

    public List<Vertice> obtenerVerticesAdyacentes(Vertice vertice) {
        List<Vertice> adyacentes = new ArrayList<>();
        for (Arista arista : aristas) {
            if (arista.getInicio().equals(vertice)) {
                adyacentes.add(arista.getFin());
            } else if (!arista.isDirigida() && arista.getFin().equals(vertice)) {
                adyacentes.add(arista.getInicio());
            }
        }
        return adyacentes;
    }

    public String generarDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        for (Arista arista : aristas) {
            sb.append(arista.getInicio().getId()).append(" -> ").append(arista.getFin().getId());
            if (!arista.isDirigida()) {
                sb.append(" [dir=none]");
            }
            sb.append(";\n");
        }
        sb.append("}");
        System.out.println(sb.toString());
        return sb.toString();
    }
    
    public List<Vertice> dfs(String idInicio) {
        Vertice inicio = obtenerVertice(idInicio);
        if (inicio == null) {
            throw new IllegalArgumentException("El vértice de inicio no existe en el grafo.");
        }

        List<Vertice> visitados = new ArrayList<>();
        dfsRecursivo(inicio, visitados);
        return visitados;
    }

    private void dfsRecursivo(Vertice actual, List<Vertice> visitados) {
        visitados.add(actual);
        for (Vertice adyacente : obtenerVerticesAdyacentes(actual)) {
            if (!visitados.contains(adyacente)) {
                dfsRecursivo(adyacente, visitados);
            }
        }
    }
    
    public String generarDotRecorrido(List<Vertice> recorrido, List<Arista> aristasRecorrido) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");

        for (Arista arista : aristas) {
            boolean aristaEnRecorrido = aristasRecorrido.stream().anyMatch(a -> a.getInicio().equals(arista.getInicio()) && a.getFin().equals(arista.getFin()));
            sb.append("\t").append(arista.getInicio().getId()).append(" -> ").append(arista.getFin().getId());

            if (aristaEnRecorrido || !arista.isDirigida()) {
                sb.append(" [");
            }

            if (aristaEnRecorrido) {
                sb.append("color=red");
                if (!arista.isDirigida()) {
                    sb.append(", ");
                }
            }

            if (!arista.isDirigida()) {
                sb.append("dir=none");
            }

            if (aristaEnRecorrido || !arista.isDirigida()) {
                sb.append("]");
            }

            sb.append(";\n");
        }

        sb.append("}");
        System.out.println(sb.toString());
        return sb.toString();
    }



    public Arista buscarArista(Vertice inicio, Vertice fin) {
        for (Arista arista : aristas) {
            if (arista.getInicio().equals(inicio) && arista.getFin().equals(fin)) {
                return arista;
            }
        }
        return null;
    }

    public Grafo generarGrafoRecorridoDFS(List<Vertice> recorrido) {
        Grafo grafoDFS = new Grafo();

        // Agregar los vértices al grafoDFS
        for (Vertice v : recorrido) {
            grafoDFS.agregarVertice(v);
        }

        // Agregar las aristas al grafoDFS
        for (int i = 0; i < recorrido.size() - 1; i++) {
            Vertice inicio = recorrido.get(i);
            Vertice fin = recorrido.get(i + 1);
            boolean aristaEncontrada = false;
            for (Arista arista : aristas) {
                if ((arista.getInicio().equals(inicio) && arista.getFin().equals(fin)) ||
                        (!arista.isDirigida() && arista.getInicio().equals(fin) && arista.getFin().equals(inicio))) {
                    grafoDFS.agregarArista(arista);
                    aristaEncontrada = true;
                    break;
                }
            }

            // Si no se encuentra la arista en el grafo original, crea una nueva arista y agrégala al grafoDFS
            if (!aristaEncontrada) {
                Arista nuevaArista = new Arista(inicio, fin, false); // Asume que no es dirigida
                grafoDFS.agregarArista(nuevaArista);
            }
        }

        return grafoDFS;
    }

}
