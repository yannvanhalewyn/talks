(ns talks.brightin
  (:require
   #?(:clj  [casper.core :as casper :refer [defslide]]
      :cljs [casper.core :as casper :refer-macros [defslide]])))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Helpers

(defn- img [url]
  [:<>
   [:img.my-auto.mx-auto {:src url}]])

(defn- welcome [& children]
  [:div
   [:p.text-gray-800 "Welcome to"]
   [:h1.title "brightin"]
   (into [:div.mt-4] children)])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Slides

(defslide welcome-1 {}
  (welcome [:span "."]))

(defslide welcome-2 {}
  (welcome "we make software"))

(defslide welcome-3 {}
  (welcome
   (casper/colorize "we make " ["beautiful" :orange-600] " software")))

(defslide welcome-4 {}
  (welcome
   [:p (casper/colorize "we make " ["people" :blue-600] " happy ")]
   [:p.mt-1 (casper/colorize "with " ["beautiful" :orange-600] " software")]))

(defslide beautiful {}
  [:<>
   [:h1.title.italic.font-serif.text-blue-500
    "B-e-a-utiful"]
   [:p.mt-1.text-gray-700.font-bold "Meaning"]
   (casper/enumeration {:class "w-32"}
     (casper/colorize "How it " ["looks"   :blue-600])
     (casper/colorize "How it " ["is used" :red-600])
     (casper/colorize "How it " ["works"   :purple-600]))])

(defslide hiring {:slide/layout :layout/blue}
  [:h1.title "And we are " [:span.text-orange-500 "hiring!"]])

(def plug
  [(casper/transition-group :none
     [welcome-1 welcome-2 welcome-3 welcome-4
      beautiful hiring])])

(defslide brightmotive-logo {}
  (img "./img/brightmotive_logo.png"))

(defslide mechanic
  {:slide/background-image "./img/mechanic.webp"
   :slide/layout :layout/none}
  nil)

(defslide aldoc {}
  (img "./img/aldoc.png"))

(defslide brightmotive {:slide/layout :layout/blue}
  [:<>
   (img "./img/brightmotive.png")
   [:aside.notes
    "Frank doesn't care if we used transducers or core.async"]])

(def brightmotive-intro
  [brightmotive-logo
   mechanic
   (casper/transition-group :none
     [aldoc brightmotive])])
