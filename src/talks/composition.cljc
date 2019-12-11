(ns talks.composition
  (:require
   #?(:clj  [casper.core :as casper :refer [defslide]]
      :cljs [casper.core :as casper :refer-macros [defslide]])
   [talks.brightin :as brightin]
   [talks.layouts  :as layouts]
   [clojure.string :as str]))

(defn- code [opts code]
  (let [{:keys [lang class]} (if (keyword? opts)
                               {:lang opts}
                               opts)]
    [:pre.text-left.mx-auto {:class class}
     [:code.p-4 {:class (str (name lang) "p-4 rounded-lg")}
      (str/trim (str code))]]))

(defslide title {}
  [:h1.title
   [:span.text-blue-600 "Composition"]
   " over "
   [:span.text-red-600 "Convention"]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Rails

(defslide used-to-be-rails {}
  [:h1.title
   (casper/colorize "We used to be a " ["Rails shop." :blue-500])])

(defslide should-we-still-use-it {}
  [:h1.title
   (casper/colorize "Should we still " ["use it?" :red-500])])

(defslide rails-initial-commit {:slide/layout :layout/none}
  [:div.flex.max-w-3xl.mx-auto
   [:div.my-auto.mr-32
    [:h1.title.-mt-12 "Rails new"]
    [:h2.text-lg.opacity-75 (casper/colorize "gem install " ["hairball" :red-500])]]
   [:img {:src "./img/rails_initial_commit.png"}]])

(defslide project-lifecycle {:slide/layout :layout/blue}
  [:<>
   [:h1.title "TODO figma.jpg"]
   [:aside.notes
    "Statistical value of joy is higher in Rails ..."]])

(def rails
  [(casper/transition-group :none
     [used-to-be-rails
      should-we-still-use-it])
   (casper/transition-group :none
     [rails-initial-commit
      project-lifecycle])])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Composition

(defslide composition-title {:slide/background-image "./img/hands.jpeg"
                             :slide/layout :layout/dark}
  [:h1.title "Composition"])

(defslide etymology {}
  [:<>
   [:h1.title.text-blue-500 "Com • ponere"]
   [:div.mx-48.mt-4.px-12.font-bold.text-gray-900
    [:span "Near"]
    [:span.ml-8 "To Place"]]])

(defslide xy-graph {}
  [:span "TODO x-y graph"])

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
   (code {:lang :lang/clojure
          :class "w-128"} "
(def accounts-report
  (comp account/summary user/all-accounts))

(accounts-report db)
")
   [:div
    (casper/enumeration {:class "w-96"}
      (casper/colorize ["def accounts-report" :orange-500] " is the "
        ["composition" :blue-500])
      (casper/colorize "of "
        ["account/summary"   :red-500] " and "
        ["user/all-accounts" :red-500])
      (casper/colorize
        "which are the " ["composable" :blue-500] " elements"))]
   [:aside.notes
    [:ul
     [:li "Notice how both functions don't know about eachother"]
     [:li "Notice that the `def` is the composition, and it is done "
      "via it's composable parts !!!! VERY IMPORTANT"]
     [:li "Don't think like 'how will they compose' but in 'can they compose'"]]]])

(def composition
  (casper/transition-group :none
    [composition-title etymology xy-graph composition-2 composition-3]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Composition in music

(defslide musician {:slide/background-image "./img/guitar.jpg"
                    :slide/layout :layout/dark}
  [:h1.title (casper/colorize "I am also a " ["musician" :pink-600])])

(defslide asylum {:slide/layout :layout/none
                  :slide/class "bg-black text-white"}
  [:video.absolute.inset-0.h-full.w-full
   {:controls true}
   [:source {:data-src "./img/asylum_snippet.mp4"}]])

(def COMP_COLOR :blue-500)
(def ARR_COLOR :orange-500)

(defn- c-v-a-title []
  [:h1.title (casper/colorize ["Composer" COMP_COLOR] " vs "
                              ["Arranger" ARR_COLOR])])

(defslide composer-v-arranger {:slide/background-image "./img/music_sheet2.jpg"
                               :slide/layout :layout/dark}
  [:<>
   (c-v-a-title)
   (casper/enumeration {:class "text-sm w-64 text-center"}
     (casper/colorize ["Composer" COMP_COLOR] " generates musical ideas")
     (casper/colorize ["Arranger" ARR_COLOR]  " assembles ideas into product")
     (casper/colorize "The " ["music    industry" :red-700] " acknowledges this")
     (casper/colorize "The " ["software industry" :red-700] " doesn't"))
   [:aside.notes
    [:ul
     [:li "Why some songs are harder to arrange than others"]
     [:li
      "Success of music industry in terms of output!! We "
      "keep coming up with new songs."]
     [:li "Questions? No? We all understand composers?"]]]])
;; TODO real book slide

(defslide c-v-a-code-example {}
  [:<>

   (c-v-a-title)
   (into
     [casper/enumeration {:class "flex flex-col justify-around h-48"}]
     (for [{:keys [title code-str]}
           [{:title ["Composer" COMP_COLOR]
             :code-str "
(defn composer []
  (random-sample (range all-musical-ideas)))
"}
            {:title ["Arranger" ARR_COLOR]
             :code-str "
(defn arranger [composition]
  (apply make-song composition))
"}
            {:title ["Make music" :black]
             :code-str "
(def make-music! (comp arranger composer))

(make-music!) ;; => \"🎼 Let it be 🎵\"
"
             }]]
       ^{:key (first title)}
       [:div.flex.flex-wrap.items-center
        [:h1.title.min-w-48 (casper/colorize title)]
        (code {:lang :lang/clojure
               :class "flex-grow"}
          code-str)]))
   [:aside.notes
    [:ul
     [:li
      "Again, notice that the composition is a list of many composable parts"]
     [:li "It would be trivial to compose different composers with different arrangers"]]]])

(def composition-in-music
  [musician
   asylum
   (casper/transition-group :none
     [composer-v-arranger c-v-a-code-example])])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Tower of composability

(defn- apples-oranges-title []
  (casper/colorize
   "Let's compare " ["apples" :green-500] " to " ["oranges" :orange-500]))

(defslide apples-and-oranges {:slide/layout :layout/dark}
  [:h1.title (apples-oranges-title)])

(defslide the-tower {}
  [:<>
   [:h1.title (apples-oranges-title)]
   (code {:lang :ascii
          :class "mt-6 w-56"} "
+---------+
| Macro's |
+-+-------+-+
  | Classes |
  +-+-------+---+
    | Functions |
    +--+--------+-+
       |   DATA   |
       +----------+
")
   [:aside.notes
    [:ul
     [:li "EDN!!! readers"]
     [:li "Clojure knows how to push side-effects to the edge"]
     [:li "We should push behavior more to the edges too"]]]])

(defslide its-a-trap {:slide/background-image "./img/its_a_trap.gif"
                      :slide/layout :layout/dark}
  [:<>
   (code {:lang :lang/ruby :class "px-24"} "
class MyHairball
  include HairballOne
  include HairballTwo
end

MyHairball.new.do_something
")
   [:aside.notes
    [:ul
     [:li "Rails tries to compose using meta programming"]]]])

(def tower-of-composability
  (casper/transition-group :none
    [apples-and-oranges the-tower its-a-trap]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Examples in the wild

(def examples
  (casper/transition-group :zoom
    (for [{:keys [name composable?]}
          [{:name "Compojure"}
           {:name "Reitit"        :composable? true}
           {:name "Ring"          :composable? true}
           {:name "Duct"          :composable? true}
           {:name "tailwind"      :composable? true}
           {:name "specql"        :composable? false}
           {:name "Fulcro RAD"    :composable? true}
           {:name "Boyscout"      :composable? true}
           {:name "These slides!" :composable? true}]]
      (let [[layout title-color] (if composable?
                                   [:layout/blue :white]
                                   [:layout/orange :orange-200])]
        (casper/make-slide {:slide/layout layout}
          [:<>
           [:h1.title (casper/colorize [name title-color])]
           [:div.absolute.left-0.bottom-5
            (if composable?
              (casper/colorize "👍" [" composable" title-color])
              (casper/colorize "👎" [" not" :white] [" composable" title-color]))]])))))

(defslide duct-fender {}
  [:h1.title "TODO: Fender"
   [:aside.notes
    "If the stars align and the elements compose, you can write "
    "Rails and Administrate in under a 100 lines of Clojure"]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Arrangement

(defslide thanks {:slide/layout :layout/blue}
  [:h1.title (casper/colorize "Thanks!")])

(defn slides
  "A rendered list of slides"
  []
  (casper/render-slides
    [title
     brightin/plug
     brightin/brightmotive-intro
     rails
     composition
     composition-in-music
     tower-of-composability
     examples
     duct-fender
     thanks]
    {:layout/default layouts/logo-light
     :layout/dark    layouts/logo-dark
     :layout/blue    layouts/logo-blue
     :layout/orange  layouts/logo-orange}))
