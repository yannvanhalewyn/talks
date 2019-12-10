(ns reveal.core
  (:require [goog.dom :as gdom]
            [talks.composition :as composition]
            [reveal]
            [reagent.core :as r]))

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

(defn- render! []
  (r/render (into [:<>] (composition/slides))
    (gdom/getElement "slides")))

(defn ^:dev/after-load main! []
  (render!)
  (reveal/initialize options)
  (highlight!))
