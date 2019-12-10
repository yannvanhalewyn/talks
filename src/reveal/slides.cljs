(ns reveal.slides
  (:require [casper.core :as casper :refer-macros [defslide]]))

(defn- welcome [& children]
  [:div
   [:p.text-gray-800 "Welcome to"]
   [:h1.title "brightin"]
   (into
    [:div.mt-4
     children])])

(defslide title {}
  [:h1.title
   [:span.text-blue-600 "Composition"]
   " over "
   [:span.text-red-600 "Convention"]])

(defslide brightin-1 {}
  (welcome [:span "."]))

(defslide brightin-2 {}
  (welcome "We like to make software"))

(defslide brightin-3 {}
  (welcome
   "We like to make " [:span.text-orange-600 "beautiful"]
   " software"))

(defslide brightin-4 {}
  (welcome
   [:p "We like to make " [:span.text-blue-600 "people"] " happy "]
   [:p.mt-2 "With " [:span.text-orange-600 "beautiful"] " software"]))

(defslide asylum {:hide-logo? true
                  :background "black"}
  [:video.absolute.inset-0.h-full.w-full
   {:src "./img/asylum_snippet.mp4"
    :controls true}])

(defslide composition {}
  [:div
   [:h1.title "Com • poser"]
   [:p "To lay near"]])

(defslide thanks {}
  [:h1.title "Thanks!"])

(defn all
  "Add here all slides you want to see in your presentation."
  []
  (casper/render-slides
   [title
    (casper/transition-group
     ["none none-out" "none" "none none-in"]
     [brightin-1 brightin-2 brightin-3 brightin-4])
    asylum
    thanks]))
