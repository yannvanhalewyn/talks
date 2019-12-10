(ns talks.composition
  (:require
   #?(:clj  [casper.core :as casper :refer [defslide]]
      :cljs [casper.core :as casper :refer-macros [defslide]])
   [clojure.string :as str]))

(defn- welcome [& children]
  [:div
   [:p.text-gray-800 "Welcome to"]
   [:h1.title "brightin"]
   (into [:div.mt-4] children)])

(defn- code [opts code]
  (let [{:keys [lang class]} (if (keyword? opts)
                               {:lang opts
                                :class "px-8"}
                               opts)]
    [:pre.text-left {:class (str class " text-left")}
     [:code {:class (str (name lang) "p-4 rounded-lg")}
      (str/trim (str code))]]))

(defslide title {}
  [:h1.title
   [:span.text-blue-600 "Composition"]
   " over "
   [:span.text-red-600 "Convention"]])

(defslide brightin-1 {}
  (welcome [:span "."]))

(defslide brightin-2 {}
  (welcome "we make software"))

(defslide brightin-3 {}
  (welcome
   "we make " [:span.text-orange-600 "beautiful"]
   " software"))

(defslide brightin-4 {}
  (welcome
   [:p "we make " [:span.text-blue-600 "peope"] " happy "]
   [:p.mt-1 "with " [:span.text-orange-600 "beautiful"] " software"]))

(def brightin-plug
  (casper/transition-group
   ["slide-in" "none" "slide-out"]
   [brightin-1 brightin-2 brightin-3 brightin-4]))

(defslide brightmotive-1 {:background-image "./img/mechanic.webp"
                          :hide-logo? true})

(defslide asylum {:hide-logo? true
                  :class "bg-black text-white"}
  [:video.absolute.inset-0.h-full.w-full
   {:controls true}
   [:source {:data-src "./img/asylum_snippet.mp4"}]])

(defslide code-slide {::casper/audiences #{:audience/ruby}}
  (code {:lang :lang/ruby :class "px-24"} "
class MyHairball
  include HairballOne
  include HairballTwo
end
"))

(defslide composition-1 {:casper/audience #{:audience/clojure}}
  [:div
   [:h1.title "Com • poser"]
   [:p "To lay near"]])

(defslide composition-2 {}
  [:div
   [:h1.title.mb-4 "Composition is powerful"]
   (code :lang/clojure "
(def can-drink?
  (comp #(>= % 21) :user/age))
")])

(defslide composition-3 {}
  [:div
   [:h1.title.mb-4 "Composition is powerful"]
   (code :lang/clojure "
(ns db.user)

(def accounts-report
  (comp account/summary user/all-accounts))
")])

(defslide thanks {}
  [:h1.title "Thanks!"])

(defn slides
  "Add here all slides you want to see in your presentation."
  []
  (casper/render-slides
   [title
    brightin-plug
    (casper/transition-group
     ["none" "none" "none"]
     [composition-1 composition-2 composition-3])
    code-slide
    brightmotive-1
    asylum
    thanks]))
