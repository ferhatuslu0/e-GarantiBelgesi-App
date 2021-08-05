<?php

use Twig\Environment;
use Twig\Error\LoaderError;
use Twig\Error\RuntimeError;
use Twig\Extension\SandboxExtension;
use Twig\Markup;
use Twig\Sandbox\SecurityError;
use Twig\Sandbox\SecurityNotAllowedTagError;
use Twig\Sandbox\SecurityNotAllowedFilterError;
use Twig\Sandbox\SecurityNotAllowedFunctionError;
use Twig\Source;
use Twig\Template;

/* table/structure/partition_definition_form.twig */
class __TwigTemplate_a454bcfc24dd64fc54a94c73c7382bfb20538aebea1954b232f9bb10f7d90707 extends \Twig\Template
{
    private $source;
    private $macros = [];

    public function __construct(Environment $env)
    {
        parent::__construct($env);

        $this->source = $this->getSourceContext();

        $this->parent = false;

        $this->blocks = [
        ];
    }

    protected function doDisplay(array $context, array $blocks = [])
    {
        $macros = $this->macros;
        // line 1
        echo "<form action=\"tbl_structure.php\" method=\"post\">
    ";
        // line 2
        echo PhpMyAdmin\Url::getHiddenInputs(($context["db"] ?? null), ($context["table"] ?? null));
        echo "
    <input type=\"hidden\" name=\"edit_partitioning\" value=\"true\">

    <fieldset>
        <legend>";
        // line 6
        echo _gettext("Edit partitioning");
        echo "</legend>
        ";
        // line 7
        $this->loadTemplate("columns_definitions/partitions.twig", "table/structure/partition_definition_form.twig", 7)->display(twig_to_array(["partition_details" =>         // line 8
($context["partition_details"] ?? null)]));
        // line 10
        echo "    </fieldset>
    <fieldset class=\"tblFooters\">
        <input class=\"btn btn-primary\" type=\"submit\" name=\"save_partitioning\" value=\"";
        // line 12
        echo _gettext("Save");
        echo "\">
    </fieldset>
</form>
";
    }

    public function getTemplateName()
    {
        return "table/structure/partition_definition_form.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  58 => 12,  54 => 10,  52 => 8,  51 => 7,  47 => 6,  40 => 2,  37 => 1,);
    }

    public function getSourceContext()
    {
        return new Source("", "table/structure/partition_definition_form.twig", "E:\\xampp\\phpMyAdmin\\templates\\table\\structure\\partition_definition_form.twig");
    }
}
