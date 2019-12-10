(ns reveal.core
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [clojure.string :refer [join]]
            [goog.dom :as gdom]
            [hiccups.runtime]
            [reveal.slides :as slides]))


(def options
  (clj->js {:controls    false
            :controlsTutorial true
            :progress    false
            :transition  "slide"
            :hash true
            :width "100%"
            :height "100%"
            :margin 0
            :minScale 1
            :maxScale 1
            :slideNumber false
            :center false
            :dependencies [{:src "node_modules/reveal.js/plugin/notes/notes.js"
                            :async true}]}))


;; -----------------------------------------------------------------------------
;; You do not need to change anything below this comment

(defn convert
  "Get list of all slides and convert them to html strings."
  []
  (let [slides (slides/all)]
    (join (map #(html %) slides))))

(defn main!
  "Get all slides, set them as innerHTML and reinitialize Reveal.js"
  []
  (set! (.. (gdom/getElement "slides") -innerHTML) (convert))
  (.initialize js/Reveal options)
  (.setState js/Reveal (.getState js/Reveal)))
