(ns talks.layouts
  (:require [casper.core :as casper]))

(defn- logo-layout [{:keys [body class]}]
  [:<>
   [:strong "brightin"]
   [:div.text-center {:class class}
    body]
   [:div.text-xs.text-right.italic
    "Yann Vanhalewyn"]])

(defn logo-light [body]
  {:slide/background-color "white"
   ::casper/render (logo-layout {:body body})})

(defn logo-dark [body]
  {:slide/background-color "#3F3F3F"
   :slide/class "text-white"
   ::casper/render (logo-layout {:body body})})

(defn logo-blue [body]
  {:slide/class "bg-blue-500 text-blue-800"
   ::casper/render (logo-layout {:body body :class "text-white"})})
