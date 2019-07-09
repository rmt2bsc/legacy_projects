package com.components.pagination {

	public class PaginationUtil {

		public function PaginationUtil() {
			return;
		}

		/**
		 * Determines the total number of pages a Number value represents.  Total page count can represent 
		 * partial pages which a partial page constitutes a number on the right side of the decimal 
		 * value that is greater than or equal to .1. 
		 * <p>
		 * Employs a custom rounding algorithm for number values with decimal points since this system 
		 * measures pages in fractions.   For example, if the round algorithm is applied a value of 18.65, 
		 * then the results of total pages will equal 19.  The decimal part of total pages (.65) is 
		 * considered to be a little over half a page, hence a page.
		 * 
		 * @parm pageNo
		 *         The decimal representing the total pages value.
		 * @return int
		 *         The round value of total pages.
		 */
		public static function getRoundedTotalPages(pageNo : Number) : int {
			var val : int;
			var tokens : Array = pageNo.toString().split(".");
			if (tokens.length == 1) {
				val = parseInt(tokens[0]);
			}
			else if (tokens.length > 1) {
				var decimal : int = parseInt(tokens[1]);
				val = parseInt(tokens[0]);
				if (decimal > 0) {
					 val++;
				}
			}
			return val;
		}	
	}
}