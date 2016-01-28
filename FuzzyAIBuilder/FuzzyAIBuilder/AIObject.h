//
//  AIObject.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 29/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Rule.h"

@interface AIObject : NSObject
- (instancetype)initWithAIName:(NSString *)AIName;
- (void)saveNewRule:(Rule*)newRule;
- (Rule *)getRuleAtIndex:(NSInteger) index;
- (void)replaceExistingRule:(NSInteger) index withRule: (Rule*)modifiedRule;
- (NSArray *)ruleList;
- (NSInteger)getNumRules;
- (NSString *)getName;

@end
