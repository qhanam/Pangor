function finallyHandler(reasonOrValue) {
    var promise = this.promise;
    var handler = this.handler;

    var ret = handler.call(promise._boundTo);

    return ret;
}