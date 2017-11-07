<template>
  <section>
    <h1>contentSet</h1>
  </section>
</template>

<script>
  export default {
    name: 'contentSet',
    mounted(){
      console.log(this.$route.params.id)
    }

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" type="text/css" scoped>

</style>
