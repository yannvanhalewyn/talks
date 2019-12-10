(ns talks.brightin
  (:require
   #?(:clj  [casper.core :as casper :refer [defslide]]
      :cljs [casper.core :as casper :refer-macros [defslide]])))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Helpers

(defn- img [url]
  [:<>
   [:img.my-auto.mx-auto {:src url}]
   [:span.mt-8 "TODO center + brightmotive-logo"]])

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
   [:p (casper/colorize "we make " ["peope" :blue-600] " happy ")]
   [:p.mt-1 (casper/colorize "with " ["beautiful" :orange-600] " software")]))

(defslide beautiful {}
  [:<>
   [:h1.title.italic.font-serif.text-blue-500
    "B-e-a-utiful"]
   [:p.mt-1.text-gray-700.font-bold "Meaning"]
   (casper/enumeration
    (casper/colorize "How it " ["looks"   :blue-600])
    (casper/colorize "How it " ["is used" :red-600])
    (casper/colorize "How it " ["works"   :purple-600]))])

(defslide hiring {:class "bg-blue-500 text-white"}
  [:h1.title "And we are " [:span.text-orange-500 "hiring!"]])

(def plug
  [(casper/transition-group
    ["slide" "none" "slide"]
    [welcome-1 welcome-2 welcome-3 welcome-4
     beautiful hiring])])

(defslide mechanic
  {:background-image "./img/mechanic.webp"
   :hide-logo? true})

(defslide aldoc {:hide-logo? true}
  (img "./img/aldoc.png"))

(defslide brightmotive {:hide-logo? true}
  (img "./img/brightmotive.png"))

(def brightmotive-intro
  [mechanic
   (casper/transition-group
    ["slide" "none" "slide"]
    [aldoc brightmotive])])
