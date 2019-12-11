(ns talks.composition
  (:require
   #?(:clj  [casper.core :as casper :refer [defslide]]
      :cljs [casper.core :as casper :refer-macros [defslide]])
   [talks.brightin :as brightin]
   [talks.layouts  :as layouts]
   [clojure.string :as str]))

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

(defslide asylum {:slide/layout :layout/none
                  :slide/class "bg-black text-white"}
  [:video.absolute.inset-0.h-full.w-full
   {:controls true}
   [:source {:data-src "./img/asylum_snippet.mp4"}]])

(defslide composition-1 {}
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

(defslide hairball {:slide/background-image "./img/its_a_trap.gif"
                    :slide/layout :layout/dark}
  (code {:lang :lang/ruby :class "px-24"} "
class MyHairball
  include HairballOne
  include HairballTwo
end
"))

(defslide thanks {:slide/layout :layout/blue}
  [:h1.title (casper/colorize "Thanks!")])

(defn slides
  "Add here all slides you want to see in your presentation."
  []
  (casper/render-slides
   [title
    brightin/plug
    brightin/brightmotive-intro
    (casper/transition-group
     ["none" "none" "none"]
     [composition-1 composition-2 composition-3])
    hairball
    asylum
    thanks]
   {:layout/default layouts/logo-light
    :layout/dark    layouts/logo-dark
    :layout/blue    layouts/logo-blue}))
