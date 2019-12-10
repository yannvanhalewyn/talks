(ns reveal.core
  (:require-macros [hiccups.core :as h])
  (:require [clojure.string :as str]
            [goog.dom :as gdom]
            [hiccups.runtime]
            [reveal.slides :as slides]
            ["reveal" :as reveal]))

(defn highlight! []
  (.forEach (.querySelectorAll js/document "pre code")
            #(.highlightBlock js/hljs %)))

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
              :callback highlight!}
             {:src "node_modules/reveal.js/plugin/notes/notes.js"
              :async true}]}))

(defn convert [slides]
  (str/join (map #(h/html %) slides)))

(defn ^:dev/after-load main!
  "Get all slides, set them as innerHTML and reinitialize Reveal.js"
  []
  (set! (.. (gdom/getElement "slides") -innerHTML)
        (convert (slides/all)))
  (reveal/initialize options)
  (highlight!))
