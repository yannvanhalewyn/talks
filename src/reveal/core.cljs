(ns reveal.core
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [clojure.string :as str]
            [goog.dom :as gdom]
            [hiccups.runtime]
            [reveal.slides :as slides]
            ["reveal.js" :as reveal]))

(def options
  (clj->js {:controls         false
            :controlsTutorial true
            :progress         false
            :transition       "slide"
            :history          true
            :hash             true
            :width            "100%"
            :height           "100%"
            :margin           0
            :minScale         1
            :maxScale         1
            :slideNumber      false
            :center           false
            :dependencies
            [{:src "https://cdn.jsdelivr.net/reveal.js/3.0.0/plugin/highlight/highlight.js"
              :async true
              :callback (fn [] (.initHighlightingOnLoad js/hljs))}
             {:src "https://cdn.jsdelivr.net/reveal.js/3.0.0/plugin/notes/notes.js"
              :async true}]}))

(defn convert
  "Get list of all slides and convert them to html strings."
  []
  (let [slides (slides/all)]
    (str/join (map #(html %) slides))))

(defn ^:dev/after-load main!
  "Get all slides, set them as innerHTML and reinitialize Reveal.js"
  []
  (set! (.. (gdom/getElement "slides") -innerHTML) (convert))
  (reveal/initialize options))
