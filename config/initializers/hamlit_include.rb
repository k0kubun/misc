module Hamlit
  class Filters
    class IncludeFile < TiltBase
      def compile(node)
        include_file node
      end

      private

      def include_file(node)
        temple = [:multi]

        path = node.value[:text].strip.split('/')
        path[path.length - 1] = "_#{path.last}"
        partial_name = path.join('/')

        data = File.read "#{Rails.root}/app/views/#{partial_name}.haml"
        include_node = Hamlit::HamlParser::ParseNode.new(:tag, 1, {text: data})
        temple << compile_with_tilt(include_node, 'haml')
        temple
      end
    end

    register :include_file, IncludeFile
  end
end
