## Server documentation

All subsequent methods should be queried like `{base_url}/api/{method_name}(?{method_args})?` \
For example: `evarand.rocks/api/regiserInn?inn=100500`

Methods:

* `regiserInn(inn : String)` -- should be called before passing this `inn` with any other queries

