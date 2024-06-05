package com.example.pathfinder.dto;

import java.util.List;

public class RequestBody {
    List<Content> contents;

    public static class Content {
        List<Part> parts;

        public static class Part {
            String text;

            public Part(String text) {
                this.text = text;
            }
        }

        public Content(List<Part> parts) {
            this.parts = parts;
        }
    }

    public RequestBody(List<Content> contents) {
        this.contents = contents;
    }
}