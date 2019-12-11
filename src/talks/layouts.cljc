(ns talks.layouts
  (:require [casper.core :as casper]))

(defn- logo-layout [{:keys [body class]}]
  [:<>
   [:strong.text-left "brightin"]
   [:div {:class class}
    body]
   [:div.text-xs.text-right.italic
    "Yann Vanhalewyn"]])

(defn logo-light [body]
  {:slide/background-color "white"
   :slide/class "text-center"
   ::casper/render (logo-layout {:body body})})

(defn logo-dark [body]
  {:slide/background-color "#3F3F3F"
   :slide/class "text-center text-white text-shadow-md"
   ::casper/render (logo-layout {:body body})})

(defn logo-blue [body]
  {:slide/class "text-center bg-blue-500 text-blue-800"
   ::casper/render (logo-layout {:body body :class "text-white"})})

(defn logo-orange [body]
  {:slide/class "text-center bg-orange-500 text-orange-800"
   ::casper/render (logo-layout {:body body :class "text-white"})})
